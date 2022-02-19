package com.thoughtworks.archguard.scanner.domain.system

interface SystemInfoRepository {

    fun getSystemInfo(id: Long): SystemInfo?
    fun updateSystemInfo(systemInfo: SystemInfo): Int
    fun updateScanningSystemToScanFail()
}