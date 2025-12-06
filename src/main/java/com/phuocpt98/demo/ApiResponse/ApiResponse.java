package com.phuocpt98.demo.ApiResponse;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    private Boolean success;
    private int code;
    private String message;
    private T data;

    // 1. Phương thức MỚI: Thành công, không có tham số (dùng cho DELETE/POST)
    public static ApiResponse<?> success() {
        return ApiResponse.builder()
                .message("Thành công")
                .success(Boolean.TRUE)
                .build();
    }

    // 2. Thành công VÀ có dữ liệu
    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.<T>builder()
                .data(data)
                .message("Thành công")
                .success(Boolean.TRUE)
                .build();
    }

    // 3. Thành công VÀ có dữ liệu + thông báo tùy chỉnh
    public static <T> ApiResponse<T> success(String message, T data) {
        return ApiResponse.<T>builder()
                .data(data)
                .message(message)
                .success(Boolean.TRUE)
                .build();
    }

    // 4. Thành công NHƯNG KHÔNG có dữ liệu (chỉ thông báo tùy chỉnh)
    public static ApiResponse<?> success(String message) {
        return ApiResponse.builder()
                .success(Boolean.TRUE)
                .message(message)
                .build();
    }

    // --- Phương thức tiện ích cho phản hồi LỖI (ERROR) ---

    // 5. Phản hồi LỖI với mã code và thông báo
    public static ApiResponse<?> error(int code, String message) {
        return ApiResponse.builder()
                .success(Boolean.FALSE)
                .code(code)
                .message(message)
                .build();
    }

    // 6. Phản hồi LỖI với code mặc định 500
    public static ApiResponse<?> error(String message) {
        return ApiResponse.builder()
                .success(Boolean.FALSE)
                .code(500)
                .message(message)
                .build();
    }
}
