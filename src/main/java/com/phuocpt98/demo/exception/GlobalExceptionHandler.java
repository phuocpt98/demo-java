package com.phuocpt98.demo.exception;

import com.phuocpt98.demo.ApiResponse.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Xử lý lỗi nghiệp vụ tùy chỉnh (Custom Business Exception)
     * Thường là các lỗi 4xx (Bad Request, Forbidden, Not Found)
     */
    @ExceptionHandler(value = AppException.class)
    public ResponseEntity<ApiResponse<?>> handlingAppException(AppException e){
        // Sử dụng mã lỗi và thông báo đã định nghĩa trong ErrorCode của AppException
        return ResponseEntity
                .badRequest() // Thường trả về 400 Bad Request
                .body(ApiResponse.error(e.getErrorCode().getCode(), e.getErrorCode().getMessage()));
    }

    // --- Xử lý Lỗi Validation Dữ liệu Đầu vào ---

    /**
     * Xử lý lỗi khi tham số không đúng kiểu dữ liệu (ví dụ: chuỗi thay vì số Long)
     */
    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ResponseEntity<ApiResponse<?>> handleMismatch(MethodArgumentTypeMismatchException e) {
        String name = e.getName(); // Tên tham số (ví dụ: userId)
        String requiredType = e.getRequiredType().getSimpleName(); // Kiểu dữ liệu yêu cầu (ví dụ: Long)

        String errorMessage = String.format("Tham số '%s' phải có kiểu dữ liệu là '%s'", name, requiredType);

        return ResponseEntity
                .badRequest() // 400 Bad Request
                .body(ApiResponse.error(ErrorCode.BAD_REQUEST.getCode(), errorMessage));
    }

    /**
     * Xử lý lỗi khi validation @Valid thất bại (ví dụ: @Min, @NotNull)
     */
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ApiResponse<?>> handleValidation(MethodArgumentNotValidException e) {
        // Lấy thông báo lỗi đầu tiên từ validation
        String errorMessage = e.getBindingResult()
                .getAllErrors().get(0)
                .getDefaultMessage();


        ErrorCode errorCode;
        try {
            // 1. Cố gắng chuyển đổi errorMessage thành tên ENUM (ví dụ: "INVALID_CREDENTIALS")
            errorCode = ErrorCode.valueOf(errorMessage);

        } catch (IllegalArgumentException ex) {
            errorCode = ErrorCode.INVALID_REQUEST_KEY;
        }


        return ResponseEntity
                .badRequest() // 400 Bad Request
                .body(ApiResponse.error(errorCode.getCode(), errorCode.getMessage()));
    }

    // --- Xử lý Lỗi Chung (Fallback) ---

    /**
     * Xử lý các lỗi Runtime (chưa được định nghĩa hoặc lỗi logic)
     * Đây là lớp cha của nhiều lỗi không mong muốn khác.
     */
    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<ApiResponse<?>> handlingRuntimeException(RuntimeException e){
        // Log lỗi chi tiết tại đây (e.printStackTrace() hoặc logger)
        System.err.println("RuntimeException: " + e.getMessage());

        // Trả về lỗi server 500
        ApiResponse<?> apiResponse = ApiResponse.error(
                ErrorCode.INTERNAL_SERVER_ERROR.getCode(),
                ErrorCode.INTERNAL_SERVER_ERROR.getMessage()
        );
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR) // Trả về HTTP 500
                .body(apiResponse);
    }

    /**
     * Xử lý tất cả các Exception còn lại (Lớp cao nhất)
     */
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ApiResponse<?>> handlingGeneralException(Exception e){
        // Log lỗi chi tiết tại đây
        System.err.println("General Exception: " + e.getMessage());

        // Trả về lỗi server 500
        ApiResponse<?> apiResponse = ApiResponse.error(
                ErrorCode.INTERNAL_SERVER_ERROR.getCode(),
                ErrorCode.INTERNAL_SERVER_ERROR.getMessage()
        );
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR) // Trả về HTTP 500
                .body(apiResponse);
    }
}