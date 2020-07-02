package com.thoughtworks.archguard.module.domain.dubbo

import com.thoughtworks.archguard.module.domain.Dependency
import com.thoughtworks.archguard.module.domain.DependencyAnalysisHelper
import com.thoughtworks.archguard.module.domain.JClassRepository
import com.thoughtworks.archguard.module.domain.LogicModule
import com.thoughtworks.archguard.module.domain.getModule
import org.springframework.stereotype.Service

@Service
class DubboXmlDependencyAnalysisHelper(var jClassRepository: JClassRepository, var xmlConfigService: XmlConfigService) : DependencyAnalysisHelper {

    override fun analysis(classDependency: Dependency, logicModules: List<LogicModule>, calleeModules: List<String>): List<String> {
        val callerJClass = jClassRepository.getJClassByName(classDependency.caller)
        val calleeJClass = jClassRepository.getJClassByName(classDependency.callee)
        val calleeSubModuleByXml = xmlConfigService.getRealCalleeModuleByDependency(callerJClass, calleeJClass)
        val xmlAnalysisModules = calleeSubModuleByXml.map { getModule(logicModules, it.name) }.flatten()
        return calleeModules.intersect(xmlAnalysisModules).toList()
    }
}