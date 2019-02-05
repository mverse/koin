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
package org.koin.experimental.builder

import org.koin.core.definition.BeanDefinition
import org.koin.core.module.Module
import kotlin.reflect.KClass

/**
 * Create a Single definition for given type T
 * @param name
 * @param createOnStart - need to be created at start
 * @param override - allow definition override
 */
inline fun <reified T : Any> Module.single(
    name: String? = null,
    createOnStart: Boolean = false,
    override: Boolean = false
): BeanDefinition<T> = single(T::class, name, createOnStart, override)

/**
 * Create a Single definition for given type T
 * @param name
 * @param createOnStart - need to be created at start
 * @param override - allow definition override
 */
fun <T : Any> Module.single(
    kClass: KClass<T>,
    name: String? = null,
    createOnStart: Boolean = false,
    override: Boolean = false
): BeanDefinition<T> = single(kClass, name, createOnStart, override) { create(kClass, this) }

/**
 * Create a Factory definition for given type T
 *
 * @param name
 * @param override - allow definition override
 */
inline fun <reified T : Any> Module.factory(
    name: String? = null,
    override: Boolean = false
): BeanDefinition<T> = factory(T::class, name, override)

/**
 * Create a Factory definition for given type T
 *
 * @param name
 * @param override - allow definition override
 */
fun <T : Any> Module.factory(
    iface: KClass<out T>,
    name: String? = null,
    override: Boolean = false
): BeanDefinition<T> = factory(iface, name, override) { create(iface, this) }

/**
 * Create a Single definition for given type T to modules and cast as R
 * @param name
 * @param createOnStart - need to be created at start
 * @param override - allow definition override
 */
inline fun <reified R : Any, reified T : R> Module.singleBy(
    name: String? = null,
    createOnStart: Boolean = false,
    override: Boolean = false
): BeanDefinition<R> {
  return single(name, createOnStart, override) { create<T>(this) as R }
}

/**
 * Create a Single definition for given type T to modules and cast as R
 * @param name
 * @param createOnStart - need to be created at start
 * @param override - allow definition override
 */
fun <R : Any, T : R> Module.singleBy(
    iface: KClass<R>,
    impl: KClass<T>,
    name: String? = null,
    createOnStart: Boolean = false,
    override: Boolean = false
): BeanDefinition<R> = single(iface, name, createOnStart, override) { create(impl, this) }

/**
 * Create a Single definition for given type T to modules and cast as R
 * @param name
 * @param createOnStart - need to be created at start
 * @param override - allow definition override
 */
fun Module.singleByAny(
    iface: KClass<*>,
    impl: KClass<*>,
    name: String? = null,
    createOnStart: Boolean = false,
    override: Boolean = false
): BeanDefinition<Any> = singleAny(iface, name, createOnStart, override) { create(impl, this) }

/**
 * Create a Factory definition for given type T to modules and cast as R
 *
 * @param name
 * @param override - allow definition override
 */
inline fun <reified R : Any, reified T : R> Module.factoryBy(
    name: String? = null,
    override: Boolean = false
): BeanDefinition<R> = factoryBy(R::class, T::class, name, override)

/**
 * Create a Factory definition for given type T to modules and cast as R
 *
 * @param name
 * @param override - allow definition override
 */
fun <R : Any, T : R> Module.factoryBy(
    iface: KClass<R>,
    impl: KClass<T>,
    name: String? = null,
    override: Boolean = false
): BeanDefinition<R> = factory(iface, name, override) { create(impl, this) }