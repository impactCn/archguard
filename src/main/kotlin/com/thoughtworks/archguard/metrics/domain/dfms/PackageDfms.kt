package com.thoughtworks.archguard.metrics.domain.dfms

import com.thoughtworks.archguard.metrics.domain.abstracts.PackageAbstractRatio
import com.thoughtworks.archguard.metrics.domain.coupling.PackageCoupling
import com.thoughtworks.archguard.module.domain.model.PackageVO

class PackageDfms private constructor(val packageVO: PackageVO, val innerInstabilityAvg: Double, val outerInstabilityAvg: Double, val absRatio: Double) {
    companion object {
        fun of(packageVO: PackageVO, packageCoupling: PackageCoupling, packageAbstractRatio: PackageAbstractRatio): PackageDfms {
            return PackageDfms(packageVO, packageCoupling.innerInstabilityAvg, packageCoupling.outerInstabilityAvg, packageAbstractRatio.ratio)
        }
    }
}
