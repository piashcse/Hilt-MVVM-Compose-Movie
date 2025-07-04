package com.xiaomi.base.templates

/**
 * Marker interface để đánh dấu template package
 * Package này có thể được xóa sau khi hoàn thành dự án
 * 
 * @author AI Generated
 * @version 1.0.0
 * @since 2024-12-19
 */
interface TemplatePackage {
    companion object {
        const val PACKAGE_NAME = "com.xiaomi.base.templates"
        const val VERSION = "1.0.0"
        const val CAN_BE_REMOVED = true
        const val DESCRIPTION = "Lego Component System - Modular template library for Android Compose"
        
        /**
         * Kiểm tra xem package có thể được xóa an toàn không
         * @return true nếu không có dependencies từ production code
         */
        fun canSafelyRemove(): Boolean {
            // TODO: Implement dependency checking logic
            return CAN_BE_REMOVED
        }
        
        /**
         * Lấy thông tin về package
         */
        fun getPackageInfo(): PackageInfo {
            return PackageInfo(
                name = PACKAGE_NAME,
                version = VERSION,
                description = DESCRIPTION,
                canRemove = CAN_BE_REMOVED,
                createdAt = System.currentTimeMillis()
            )
        }
    }
}

/**
 * Thông tin về template package
 */
data class PackageInfo(
    val name: String,
    val version: String,
    val description: String,
    val canRemove: Boolean,
    val createdAt: Long
)