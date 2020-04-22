package id.husna.benchglide.di

import java.util.concurrent.ConcurrentHashMap

/**
 *  ImageBenchmarkBase
 *  Created by hanhan.firmansyah on 16/04/20.
 *
 */
object Injector {
    val maps: MutableMap<Class<*>, Any> = ConcurrentHashMap()

    inline fun <reified T> get(): T {
        return maps[T::class.java] as T
    }

    inline fun <reified T : Module> provide() {
        try {
            T::class.java.newInstance().provide()
        } catch (e: Throwable) {
            throw IllegalStateException("Cannot process", e)
        }
    }

    inline fun <reified T> provide(instance: Any) {
        add(T::class.java, instance)
    }

    fun add(clazz: Class<*>, obj: Any) {
        if (maps.containsKey(clazz)) {
            maps.remove(clazz)
        }
        maps[clazz] = obj
    }

    fun remove(any: Any) {
        maps.remove(any.javaClass)
    }

    fun add(obj: Any) {
        add(obj.javaClass, obj)
    }
}