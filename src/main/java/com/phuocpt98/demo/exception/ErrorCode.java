package com.phuocpt98.demo.exception;

import feign.FeignException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import lombok.Getter;
import org.springframework.http.HttpStatus;


    @Getter
    public enum ErrorCode {
        // Ví dụ về các mã lỗi phổ biến
        SUCCESS(200, "Thành công.", HttpStatus.OK),

        BAD_REQUEST(400, "Yêu cầu không hợp lệ.", HttpStatus.BAD_REQUEST),
        UNAUTHORIZED_FAIL(401, "Xac thuc loi.", HttpStatus.UNAUTHORIZED),
        UNAUTHORIZED(403, "Không có quyền truy cập.", HttpStatus.FORBIDDEN),
        NOT_FOUND(404, "Không tìm thấy tài nguyên.", HttpStatus.NOT_FOUND), // Cần bổ sung HttpStatus.NOT_FOUND

        // Lỗi nội bộ của máy chủ
        INTERNAL_SERVER_ERROR(500, "Lỗi máy chủ nội bộ không xác định.", HttpStatus.INTERNAL_SERVER_ERROR), // Cần bổ sung HttpStatus.INTERNAL_SERVER_ERROR

        // Lỗi tùy chỉnh cho nghiệp vụ (ví dụ: bắt đầu từ 6000)
        // Đối với các lỗi nghiệp vụ, thường dùng BAD_REQUEST (400) hoặc CONFLICT (409)
        // nếu không có mã HTTP nào phù hợp hơn.
        USER_NOT_ACTIVE(6001, "Người dùng chưa được kích hoạt.", HttpStatus.BAD_REQUEST),
        INVALID_REQUEST(1001, "Loi input", HttpStatus.BAD_REQUEST),
        INVALID_REQUEST_KEY(1002, "Loi input key", HttpStatus.BAD_REQUEST),

        USER_NOT_EXITS(1003, "User not exits", HttpStatus.NOT_FOUND),
        INVALID_PASSWORD(1004, "Loi input password minLength: {min}, maxLength: {max}", HttpStatus.BAD_REQUEST),
        INVALID_CREDENTIALS(6002, "Tên đăng nhập hoặc mật khẩu không đúng.", HttpStatus.UNAUTHORIZED),

        ; // Thường là 401 cho lỗi đăng nhập





    ErrorCode(int code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }

    private int code;
    private String message;
    private HttpStatus httpStatus;


}



