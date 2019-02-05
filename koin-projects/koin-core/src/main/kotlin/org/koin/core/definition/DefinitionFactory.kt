package org.koin.core.definition

import org.koin.core.scope.setScopeName
import kotlin.reflect.KClass

object DefinitionFactory {

  inline fun <reified T : Any> createSingle(
      name: String? = null,
      noinline definition: Definition<T>
  ): BeanDefinition<T> {
    return createDefinition(name, definition)
  }

  fun <T : Any> createSingle(
      type: KClass<T>,
      name: String? = null,
      definition: Definition<T>
  ): BeanDefinition<T> {
    return createDefinition(type, name, definition)
  }

  fun createSingleAny(
      type: KClass<*>,
      name: String? = null,
      definition: Definition<Any>
  ): BeanDefinition<Any> {
    return createDefinitionAny(type, name, definition)
  }

  inline fun <reified T : Any> createScope(
      name: String? = null,
      scopeName: String? = null,
      noinline definition: Definition<T>
  ): BeanDefinition<T> {
    val beanDefinition = createDefinition(name, definition, Kind.Scope)
    scopeName?.let { beanDefinition.setScopeName(scopeName) }
    return beanDefinition
  }

  inline fun <reified T : Any> createFactory(
      name: String? = null,
      noinline definition: Definition<T>
  ): BeanDefinition<T> {
    return createDefinition(name, definition, Kind.Factory)
  }

  fun <T : Any> createFactory(
      type: KClass<T>,
      name: String? = null,
      definition: Definition<T>
  ): BeanDefinition<T> {
    return createDefinition(type, name, definition, Kind.Factory)
  }

  inline fun <reified T : Any> createDefinition(
      name: String?,
      noinline definition: Definition<T>,
      kind: Kind = Kind.Single
  ): BeanDefinition<T> = DefinitionFactory.createDefinition(T::class, name, definition, kind)

  fun <T : Any> createDefinition(
      type: KClass<T>,
      name: String?,
      definition: Definition<T>,
      kind: Kind = Kind.Single
  ): BeanDefinition<T> {
    val beanDefinition = BeanDefinition<T>(name, type)
    beanDefinition.definition = definition
    beanDefinition.kind = kind
    beanDefinition.createInstanceHolder()
    return beanDefinition
  }

  fun createDefinitionAny(
      type: KClass<*>,
      name: String?,
      definition: Definition<Any>,
      kind: Kind = Kind.Single
  ): BeanDefinition<Any> {
    val beanDefinition = BeanDefinition<Any>(name, type)
    beanDefinition.definition = definition
    beanDefinition.kind = kind
    beanDefinition.createInstanceHolder()
    return beanDefinition
  }
}