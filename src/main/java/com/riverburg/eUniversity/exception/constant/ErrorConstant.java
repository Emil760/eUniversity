package com.riverburg.eUniversity.exception.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorConstant {

    //BASE
    INVALID_PARAMETERS(HttpStatus.BAD_REQUEST, 800, "Not all parameters are valid"),
    UNIQUE_VALIDATION(HttpStatus.BAD_REQUEST, 801, "Some data already exists"),
    NOT_SUPPORTED_IMAGE(HttpStatus.BAD_REQUEST, 802, "Not supported image type"),
    NOT_SUPPORTED_HTML(HttpStatus.BAD_REQUEST, 803, "Not supported html file"),
    INVALID_DATES(HttpStatus.BAD_REQUEST, 804, "Invalid dates"),

    NOT_FOUND(HttpStatus.NOT_FOUND, 998, "Not found"),
    SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 999, "Server error"),

    //AUTH
    LOGIN_FAILED(HttpStatus.NOT_FOUND, 100, "Login or password is incorrect"),
    ACCESS_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, 101, "Access token is expired"),
    ACCESS_TOKEN_INVALIDATED(HttpStatus.UNAUTHORIZED, 102, "Access token is incorrect"),
    ACCESS_TOKEN_REQUIRED(HttpStatus.UNAUTHORIZED, 103, "Access token is required"),
    REFRESH_TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND, 104, "Refresh token not found"),
    REFRESH_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, 105, "Refresh token is expired"),
    ACCOUNT_IS_DEACTIVATED(HttpStatus.BAD_REQUEST, 106, "Account is disabled"),
    DUPLICATE_PASSWORD(HttpStatus.BAD_REQUEST, 107, "Password must not be the same"),
    PASSWORD_NOT_CHANGED(HttpStatus.BAD_REQUEST, 108, "Password didn't change"),
    MAIL_NOT_FOUND(HttpStatus.NOT_FOUND, 109, "Mail not found"),
    RESET_TOKEN_MAIL_NOT_FOUND(HttpStatus.NOT_FOUND, 110, "Reset token mail not found"),

    //ACCOUNT
    ACCOUNT_NOT_FOUND(HttpStatus.NOT_FOUND, 10001, "Account not found"),
    TEACHER_NOT_FOUND(HttpStatus.NOT_FOUND, 10002, "Teacher not found"),
    STUDENT_NOT_FOUND(HttpStatus.NOT_FOUND, 10003, "Student not found"),
    ADMIN_NOT_FOUND(HttpStatus.NOT_FOUND, 10004, "Admin not found"),

    //FACULTY
    FACULTY_NOT_FOUND(HttpStatus.NOT_FOUND, 20001, "Faculty not found"),
    ACTIVE_FACULTY_NOT_FOUND(HttpStatus.NOT_FOUND, 20002, "Active faculty not found"),
    FACULTY_USED_BY_DISCIPLINE(HttpStatus.BAD_REQUEST, 20003, "Cant delete faculty because it is used by discipline"),
    FACULTY_USED_BY_GROUP(HttpStatus.BAD_REQUEST, 20004, "Cant delete faculty because it is used by group"),
    DISCIPLINE_SEMESTER_NUMBER_EXCEEDED(HttpStatus.BAD_REQUEST, 20005, "Discipline`s semester is bigger than semester count in faculty"),

    //DISCIPLINE
    DISCIPLINE_NOT_FOUND(HttpStatus.NOT_FOUND, 30001, "Discipline not found"),
    ACTIVE_DISCIPLINE_NOT_FOUND(HttpStatus.NOT_FOUND, 30002, "Active discipline not found"),
    DISCIPLINE_USED_BY_FACULTY(HttpStatus.BAD_REQUEST, 30003, "Cant delete discipline because it is used by faculty"),
    DISCIPLINE_USED_BY_TEACHER(HttpStatus.BAD_REQUEST, 30004, "Cant delete discipline because it is used by teacher"),

    //DEGREE
    DEGREE_NOT_FOUND(HttpStatus.NOT_FOUND, 40001, "Degree not found"),
    DEGREE_USED(HttpStatus.BAD_REQUEST, 40002, "Cant delete degree because it is used"),

    //SECTOR
    SECTOR_NOT_FOUND(HttpStatus.NOT_FOUND, 50001, "Sector not found"),
    SECTOR_USED_BY_GROUP(HttpStatus.BAD_REQUEST, 50002, "Cant delete degree because it is used by group"),
    SECTOR_USED_BY_TEACHER(HttpStatus.BAD_REQUEST, 50003, "Cant delete degree because it is used by teacher"),

    //EDU_PROCESS
    EDU_PROCESS_NOT_FOUND(HttpStatus.NOT_FOUND, 60001, "Educational process not found"),
    EDU_PROCESS_USED(HttpStatus.NOT_FOUND, 60002, "Cant delete educational process because it is used"),
    EDU_PROCESS_USED_BY_THEME(HttpStatus.BAD_REQUEST, 60003, "Cant delete educational process because it is used by discipline plan"),
    EDU_PROCESS_USED_BY_SCHEDULE(HttpStatus.BAD_REQUEST, 60004, "Cant delete educational process because it is used by schedule"),
    EDU_PROCESS_USED_BY_DISCIPLINE_PLAN(HttpStatus.BAD_REQUEST, 60005, "Cant delete educational process because it is used by theme"),
    EDU_PROCESS_USED_BY_ROOM(HttpStatus.BAD_REQUEST, 60006, "Cant delete educational process because it is used by room"),

    //ACADEMIC_DEGREE
    ACADEMIC_DEGREE_NOT_FOUND(HttpStatus.NOT_FOUND, 70001, "Academic degree not found"),
    ACADEMIC_DEGREE_USED_BY_TEACHER(HttpStatus.BAD_REQUEST, 70002, "Cant delete because used by teacher"),

    //Group
    GROUP_NOT_FOUND(HttpStatus.NOT_FOUND, 80001, "Group not found"),
    GROUP_USED_BY_STUDENTS(HttpStatus.BAD_REQUEST, 80002, "Cant delete group because it already have assigned students"),

    //FILE
    FILE_NOT_FOUND(HttpStatus.NOT_FOUND, 90001, "File not found"),

    //PAID_FACULTY
    PAID_FACULTY_NOT_FOUND(HttpStatus.NOT_FOUND, 11001, "Paid faculty not found"),

    //ROOM
    ROOM_NOT_FOUND(HttpStatus.NOT_FOUND, 12001, "Room not found"),
    ROOM_USED_IN_SCHEDULE(HttpStatus.NOT_FOUND, 12002, "Room used in schedule"),

    //SCHEDULE
    SCHEDULE_NOT_FOUND(HttpStatus.NOT_FOUND, 13001, "Schedule not found"),

    //GROUP_MATERIAL
    GROUP_MATERIAL_NOT_FOUND(HttpStatus.NOT_FOUND, 14001, "Group material not found"),

    //JOURNAL
    JOURNAL_NOT_FOUND(HttpStatus.NOT_FOUND, 15001, "Journal not found"),

    //THEME
    THEME_NOT_FOUND(HttpStatus.NOT_FOUND, 16001, "Theme not found"),
    THEME_ORDER_IS_INCORRECT(HttpStatus.BAD_REQUEST, 16002, "Theme order is incorrect (check min and max value)"),

    //NOMINATION
    NOMINATION_NOT_FOUND(HttpStatus.NOT_FOUND, 17001, "Nomination not found"),
    NOMINATION_ALREADY_MADE(HttpStatus.BAD_REQUEST, 17002, "Nomination was already made"),

    // STUDENT HWS
    WORK_NOT_FOUND(HttpStatus.NOT_FOUND, 18001, "Homework not found"),
    HOMEWORK_TIME_EXPIRED(HttpStatus.BAD_REQUEST, 18002, "Time to upload homework is expired"),
    HOMEWORK_HAS_GRADE(HttpStatus.BAD_REQUEST, 18003, "Homework has already grade, cannot re-upload"),

    //Material
    MATERIAL_NOT_FOUND(HttpStatus.NOT_FOUND, 19001, "Material not found");

    private HttpStatus httpStatus;

    private Integer statusCode;

    private String message;
}
