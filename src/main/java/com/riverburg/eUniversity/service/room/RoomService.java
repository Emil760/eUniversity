package com.riverburg.eUniversity.service.room;

import com.riverburg.eUniversity.exception.RestException;
import com.riverburg.eUniversity.model.dto.request.base.PaginationRequest;
import com.riverburg.eUniversity.model.dto.request.post.AddRoomRequest;
import com.riverburg.eUniversity.model.dto.request.put.UpdateRoomRequest;
import com.riverburg.eUniversity.model.dto.response.RoomResponse;
import com.riverburg.eUniversity.model.dto.response.base.DDLResponse;
import com.riverburg.eUniversity.model.dto.response.base.PaginatedListResponse;
import com.riverburg.eUniversity.model.entity.RoomEntity;

import java.util.List;

public interface RoomService {

    PaginatedListResponse<RoomResponse> getPaginatedRooms(PaginationRequest request, int active);

    List<DDLResponse<Integer>> getRoomsDDL();

    void addRoom(AddRoomRequest request) throws RestException;

    void editRoom(UpdateRoomRequest request) throws RestException;

    void activateRoom(int id, boolean isActive) throws RestException;

    void deleteRoom(int id) throws RestException;

    RoomEntity findById(int id) throws RestException;
}
