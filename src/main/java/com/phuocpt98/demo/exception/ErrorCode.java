package com.phuocpt98.demo.exception;


import lombok.Getter;


@Getter
public enum ErrorCode {
    // Ví dụ về các mã lỗi phổ biến
    SUCCESS(200, "Thành công."),

    BAD_REQUEST(400, "Yêu cầu không hợp lệ."),
    UNAUTHORIZED(401, "Không có quyền truy cập."),
    FORBIDDEN(403, "Truy cập bị cấm."),
    NOT_FOUND(404, "Không tìm thấy tài nguyên."),

    // Lỗi nội bộ của máy chủ
    INTERNAL_SERVER_ERROR(500, "Lỗi máy chủ nội bộ không xác định."),

    // Lỗi tùy chỉnh cho nghiệp vụ (ví dụ: bắt đầu từ 6000)
    USER_NOT_ACTIVE(6001, "Người dùng chưa được kích hoạt."),
    INVALID_REQUEST(1001, "Loi input"),
    INVALID_REQUEST_KEY(1002, "Loi input key"),
    INVALID_CREDENTIALS(6002, "Tên đăng nhập hoặc mật khẩu không đúng.");



    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private int code;
    private String message;

}



