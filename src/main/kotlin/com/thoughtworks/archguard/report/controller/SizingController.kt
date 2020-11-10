package com.thoughtworks.archguard.report.controller

import com.thoughtworks.archguard.report.controller.coupling.FilterSizingDto
import com.thoughtworks.archguard.report.domain.ValidPagingParam.validFilterParam
import com.thoughtworks.archguard.report.domain.sizing.*
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/systems/{systemId}/sizing")
@Validated
class SizingController(val sizingService: SizingService) {

    @GetMapping("/modules/above-line-threshold")
    fun getModulesAboveLineThreshold(@PathVariable("systemId") systemId: Long,
                                     @RequestParam(value = "numberPerPage") limit: Long,
                                     @RequestParam(value = "currentPageNumber") currentPageNumber: Long)
            : ResponseEntity<ModulesSizingListDto> {
        val offset = (currentPageNumber - 1) * limit
        val (data, count, threshold) = sizingService.getModuleSizingListAboveLineThreshold(systemId, limit, offset)
        return ResponseEntity.ok(ModulesSizingListDto(data, count, offset / limit + 1, threshold))
    }

    @GetMapping("/modules/above-threshold")
    fun getModulesAboveThreshold(@PathVariable("systemId") systemId: Long,
                                 @RequestParam(value = "numberPerPage") limit: Long,
                                 @RequestParam(value = "currentPageNumber") currentPageNumber: Long)
            : ResponseEntity<ModulesSizingListDto> {
        val offset = (currentPageNumber - 1) * limit
        val (data, count, threshold) = sizingService.getModulePackageCountSizingAboveThreshold(systemId, limit, offset)
        return ResponseEntity.ok(ModulesSizingListDto(data, count, offset / limit + 1, threshold))
    }

    @GetMapping("/packages/above-line-threshold")
    fun getPackagesAboveLineThreshold(@PathVariable("systemId") systemId: Long,
                                      @RequestParam(value = "numberPerPage") limit: Long,
                                      @RequestParam(value = "currentPageNumber") currentPageNumber: Long)
            : ResponseEntity<PackagesSizingListDto> {
        val offset = (currentPageNumber - 1) * limit
        val (data, count, threshold) = sizingService.getPackageSizingListAboveLineThreshold(systemId, limit, offset)
        return ResponseEntity.ok(PackagesSizingListDto(data, count, offset / limit + 1, threshold))

    }

    @GetMapping("/packages/above-threshold")
    fun getPackagesAboveThreshold(@PathVariable("systemId") systemId: Long,
                                  @RequestParam(value = "numberPerPage") limit: Long,
                                  @RequestParam(value = "currentPageNumber") currentPageNumber: Long)
            : ResponseEntity<PackagesSizingListDto> {
        val offset = (currentPageNumber - 1) * limit
        val (data, count, threshold) = sizingService.getPackageClassCountSizingAboveThreshold(systemId, limit, offset)
        return ResponseEntity.ok(PackagesSizingListDto(data, count, offset / limit + 1, threshold))
    }

    @PostMapping("/methods/above-threshold")
    fun getMethodsAboveLineThreshold(@PathVariable("systemId") systemId: Long,
                                     @RequestBody @Valid filterSizing: FilterSizingDto)
            : ResponseEntity<MethodSizingListDto> {

        val request = validFilterParam(filterSizing)
        val (data, count, threshold) = sizingService.getMethodSizingListAboveLineThresholdByRequestSizing(systemId, request.toFilterSizing())
        return ResponseEntity.ok(MethodSizingListDto(data, count, request.getOffset() / request.getLimit() + 1, threshold))
    }

    @PostMapping("/classes/above-line-threshold")
    fun getClassesAboveLineThreshold(@PathVariable("systemId") systemId: Long,
                                     @RequestBody @Valid filterSizing: FilterSizingDto)
            : ResponseEntity<ClassSizingListWithLineDto> {
        val request = validFilterParam(filterSizing)
        val (data, count, threshold) = sizingService.getClassSizingListAboveLineThreshold(systemId, request.toFilterSizing())
        return ResponseEntity.ok(ClassSizingListWithLineDto(data, count, request.getOffset() / request.getLimit() + 1, threshold))
    }


    @GetMapping("/classes/above-method-count-threshold")
    fun getClassesAboveMethodCountThreshold(@PathVariable("systemId") systemId: Long,
                                            @RequestBody @Valid filterSizing: FilterSizingDto)
            : ResponseEntity<ClassSizingListWithMethodCountDto> {
        val request = validFilterParam(filterSizing)
        val (data, count, threshold) = sizingService.getClassSizingListAboveMethodCountThreshold(systemId, request.toFilterSizing())
        return ResponseEntity.ok(ClassSizingListWithMethodCountDto(data, count, request.getOffset() / request.getLimit() + 1, threshold))
    }


}

data class MethodSizingListDto(val data: List<MethodSizing>, val count: Long, val currentPageNumber: Long, val threshold: Int)

data class ClassSizingListWithLineDto(val data: List<ClassSizingWithLine>, val count: Long, val currentPageNumber: Long, val threshold: Int)

data class PackagesSizingListDto(val data: List<PackageSizing>, val count: Long, val currentPageNumber: Long, val threshold: Int)

data class ModulesSizingListDto(val data: List<ModuleSizing>, val count: Long, val currentPageNumber: Long, val threshold: Int)

data class ClassSizingListWithMethodCountDto(val data: List<ClassSizingWithMethodCount>, val count: Long, val currentPageNumber: Long, val threshold: Int)