package com.riverburg.eUniversity.service.admin;

import com.riverburg.eUniversity.exception.RestException;
import com.riverburg.eUniversity.exception.constant.ErrorConstant;
import com.riverburg.eUniversity.mapper.DataMapper;
import com.riverburg.eUniversity.model.constant.Role;
import com.riverburg.eUniversity.model.dto.request.base.PaginationRequest;
import com.riverburg.eUniversity.model.dto.request.post.RegistrationRequest;
import com.riverburg.eUniversity.model.dto.request.put.UpdateAdminRequest;
import com.riverburg.eUniversity.model.dto.response.admin.AdminResponse;
import com.riverburg.eUniversity.model.dto.response.base.PaginatedListResponse;
import com.riverburg.eUniversity.model.entity.AccountEntity;
import com.riverburg.eUniversity.model.entity.AdminEntity;
import com.riverburg.eUniversity.repository.user.AdminRepository;
import com.riverburg.eUniversity.service.account.AccountService;
import com.riverburg.eUniversity.service.group.GroupService;
import com.riverburg.eUniversity.service.security.AccountAuthService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class AdminsServiceImpl implements AdminService {

    private final AdminRepository adminRepository;

    private final AccountAuthService accountAuthService;

    private final AccountService accountService;

    private final GroupService groupService;

    private final DataMapper dataMapper;

    @Override
    @Transactional
    public void register(RegistrationRequest request) {
        AccountEntity account = accountAuthService.register(request, Role.ADMIN);

        AdminEntity admin = new AdminEntity();
        admin.setAccountEntity(account);

        adminRepository.save(admin);
    }

    @Override
    public PaginatedListResponse<AdminResponse> getAdmins(PaginationRequest pagination, int active) {
        Pageable pageable = PageRequest.of(pagination.getPageIndex(), pagination.getPageSize());

        var adminsPage = adminRepository.findAdmins(pageable, pagination.getSearch(), active);

        var admins = adminsPage
                .getContent()
                .stream()
                .map(dataMapper::adminEntityToBaseUserResponse)
                .toList();

        var itemsCount = adminsPage.getTotalElements();

        return new PaginatedListResponse<AdminResponse>(admins, itemsCount);
    }

    @Override
    public void updateAdmin(UpdateAdminRequest request) {
        var account = accountService.findById(request.getAccountId());

        account.setFullName(request.getFullName());
        account.setMail(request.getMail());
        account.setAge(request.getAge());

        var admin = findById(request.getId());

        adminRepository.save(admin);
    }

    @Override
    public AdminEntity findById(int id) {
        return adminRepository.findById(id)
                .orElseThrow(() -> {
                    throw RestException.of(ErrorConstant.ADMIN_NOT_FOUND);
                });
    }
}
