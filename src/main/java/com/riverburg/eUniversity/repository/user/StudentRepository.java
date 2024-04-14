package com.riverburg.eUniversity.repository.user;

import com.riverburg.eUniversity.model.entity.StudentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface StudentRepository extends JpaRepository<StudentEntity, Integer> {

    @Query(value = "select s " +
            "from StudentEntity s " +
            "left join GroupEntity g on g.id = s.groupEntity.id " +
            "left join AccountEntity a on a.id = s.accountEntity.id " +
            "where (a.fullName like %:search% " +
            "       or a.login like %:search% " +
            "       or a.mail like %:search% " +
            "       or (case when g.name is null then '' else g.name end) like %:search%)" +
            "and (:groupId = 0 or :groupId = g.id) " +
            "and (:active = -1 " +
            "     or a.isActive = cast(:active as boolean)) " +
            "order by s.ball desc")
    Page<StudentEntity> findStudents(Pageable pageable,
                                     @Param("search") String search,
                                     @Param("groupId") int groupId,
                                     @Param("active") int active);

    @Query(value = "select s " +
            "from StudentEntity s " +
            "where s.groupEntity.id = :groupId")
    List<StudentEntity> findByGroup(@Param("groupId") Integer groupId);


    @Query(value = "select s " +
            "from AccountEntity a " +
            "inner join StudentEntity s on s.accountEntity.id = a.id " +
            "where a.id = :accountId")
    StudentEntity getStudentByAccountId(@Param("accountId") UUID accountId);


    Optional<StudentEntity> findByAccountEntityId(UUID accountId);


}

