package org.koin.experimental.builder

import org.koin.core.KoinApplication.Companion.logger
import org.koin.core.definition.DefinitionContext
import org.koin.core.logger.Level
import org.koin.core.module.Module
import org.koin.core.time.measureDuration
import java.lang.reflect.Constructor
import java.util.concurrent.ConcurrentHashMap
import kotlin.reflect.KClass

/**
 * Create instance for type T and inject dependencies into 1st constructor
 */
inline fun <reified T : Any> Module.create(context: DefinitionContext): T = create(T::class, context)

fun <T : Any> Module.create(kClass:KClass<T>, context: DefinitionContext): T {
    val kclassAsString = kClass.toString()

    val (ctor, ctorDuration) = measureDuration {
        kClass.getFirstJavaConstructor()
    }

    val (args, argsDuration) = measureDuration {
        getArguments(ctor, context)
    }

    val (instance, instanceDuration) = measureDuration {
        ctor.makeInstance(kClass, args)
    }

    if (logger.isAt(Level.DEBUG)) {
        logger.debug("| autocreate '$kClass'")
        logger.debug("| got ctor '$kclassAsString' in '$ctorDuration'")
        logger.debug("| got args '$kclassAsString' in '$argsDuration'")
        logger.debug("| got instance '$kclassAsString' in '$instanceDuration'")
    }

    return instance
}

/**
 * Make an instance with given arguments
 */
inline fun <reified T : Any> Constructor<*>.makeInstance(args: Array<Any>) =
        newInstance(*args) as T

/**
 * Make an instance with given arguments
 */
@Suppress("UNCHECKED_CAST")
fun <T : Any> Constructor<*>.makeInstance(kClass: KClass<T>, args: Array<Any>) =
    newInstance(*args) as T

/**
 * Retrieve arguments for given constructor
 */
fun getArguments(ctor: Constructor<*>, context: DefinitionContext) =
        ctor.parameterTypes.map { context.getWithDefault(it.kotlin) }.toTypedArray()

/**
 * Get first java constructor
 */
fun KClass<*>.getFirstJavaConstructor(): Constructor<*> {
    return allConstructors[this] ?: saveConstructor()
}

/**
 * Extract constructor and save it to constructors index
 */
fun KClass<*>.saveConstructor(): Constructor<*> {
    val clazz = this.java
    val ctor = clazz.constructors.firstOrNull() ?: error("No constructor found for class '$clazz'")
    allConstructors[this] = ctor
    return ctor
}

val allConstructors = ConcurrentHashMap<KClass<*>, Constructor<*>>()

/**
 * Retrieve linked dependency with defaults params
 */
internal fun <T : Any> DefinitionContext.getWithDefault(
        clazz: KClass<T>
): T = koin.get(clazz, null, null, null)