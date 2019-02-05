/*
 * Copyright 2017-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.koin.core.module

import org.koin.core.definition.BeanDefinition
import org.koin.core.definition.Definition
import org.koin.core.definition.DefinitionFactory
import org.koin.core.definition.Options
import org.koin.core.scope.ScopeDefinition
import org.koin.ext.getFullName
import kotlin.reflect.KClass

/**
 * Koin Module
 * Gather/help compose Koin definitions
 *
 * @author Arnaud Giuliani
 */
class Module(
        internal val isCreatedAtStart: Boolean,
        internal val override: Boolean
) {
    internal val definitions = arrayListOf<BeanDefinition<*>>()
    internal val scopes = arrayListOf<ScopeDefinition>()

    /**
     * Declare a definition in current Module
     */
    fun <T> declareDefinition(definition: BeanDefinition<T>, options: Options) {
        definition.updateOptions(options)
        definitions.add(definition)
    }

    /**
     * Declare a definition in current Module
     */
    fun declareScope(scope: ScopeDefinition) {
        scopes.add(scope)
    }

    /**
     * Declare a Single definition
     * @param name
     * @param createdAtStart
     * @param override
     * @param definition - definition function
     */
    inline fun <reified T:Any> single(
            name: String? = null,
            createdAtStart: Boolean = false,
            override: Boolean = false,
            noinline definition: Definition<T>
    ): BeanDefinition<T> = single(T::class, name, createdAtStart, override, definition)

    /**
     * Declare a Single definition
     * @param name
     * @param createdAtStart
     * @param override
     * @param definition - definition function
     */
    fun <T:Any> single(
        type: KClass<out T>,
        name: String? = null,
        createdAtStart: Boolean = false,
        override: Boolean = false,
        definition: Definition<T>
    ): BeanDefinition<T> {
        val beanDefinition = DefinitionFactory.createSingle(type, name, definition)
        declareDefinition(beanDefinition, Options(createdAtStart, override))
        return beanDefinition
    }

    /**
     * Declare a Single definition
     * @param name
     * @param createdAtStart
     * @param override
     * @param definition - definition function
     */
    fun singleAny(
        type: KClass<*>,
        name: String? = null,
        createdAtStart: Boolean = false,
        override: Boolean = false,
        definition: Definition<Any>
    ): BeanDefinition<Any> {
        val beanDefinition = DefinitionFactory.createSingleAny(type, name, definition)
        declareDefinition(beanDefinition, Options(createdAtStart, override))
        return beanDefinition
    }

    private fun BeanDefinition<*>.updateOptions(options: Options) {
        this.options.isCreatedAtStart = options.isCreatedAtStart || isCreatedAtStart
        this.options.override = options.override || override
    }

    /**
     * Declare a group a scoped definition with a given scope name
     * @param scopeName
     */
    fun scope(scopeName: String, scopeDefinition: ScopeDefinition.() -> Unit) {
        val scope: ScopeDefinition = ScopeDefinition(scopeName, this).apply(scopeDefinition)
        declareScope(scope)
    }

    /**
     * Declare a group a scoped definition with a given type name
     */
    inline fun <reified T> scope(scopeDefinition: ScopeDefinition.() -> Unit) {
        val scopeName = T::class.getFullName()
        val scope: ScopeDefinition = ScopeDefinition(scopeName, this).apply(scopeDefinition)
        declareScope(scope)
    }

    /**
     * Declare a ScopeInstance definition
     * @param scopeName
     * @param name
     * @param override
     * @param definition - definition function
     */
    inline fun <reified T:Any> scoped(
            name: String? = null,
            override: Boolean = false,
            noinline definition: Definition<T>
    ): BeanDefinition<T> {
        val beanDefinition = DefinitionFactory.createScope(name, definition = definition)
        declareDefinition(beanDefinition, Options(override = override))
        return beanDefinition
    }

    /**
     * Declare a Factory definition
     * @param name
     * @param override
     * @param definition - definition function
     */
    inline fun <reified T:Any> factory(
            name: String? = null,
            override: Boolean = false,
            noinline definition: Definition<T>
    ): BeanDefinition<T> {
        val beanDefinition = DefinitionFactory.createFactory(name, definition)
        declareDefinition(beanDefinition, Options(override = override))
        return beanDefinition
    }

    /**
     * Declare a Factory definition
     * @param name
     * @param override
     * @param definition - definition function
     */
    fun <T:Any> factory(
        type: KClass<out T>,
        name: String? = null,
        override: Boolean = false,
        definition: Definition<T>
    ): BeanDefinition<T> {
        val beanDefinition = DefinitionFactory.createFactory(type, name, definition)
        declareDefinition(beanDefinition, Options(override = override))
        return beanDefinition
    }

    /**
     * Help write list of Modules
     */
    operator fun plus(module: Module) = listOf(this, module)
}

/**
 * Help write list of Modules
 */
operator fun List<Module>.plus(module: Module): List<Module> = this + listOf(module)