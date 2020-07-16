package com.greyhairredbear.apitemplate.dal

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object Versions: IntIdTable() {
    val major = integer("major")
    val minor = integer("minor")
    val patch = integer("patch")
}

class Version(id: EntityID<Int>): IntEntity(id) {
    companion object : IntEntityClass<Version>(Versions)
    var major by Versions.major
    var minor by Versions.minor
    var patch by Versions.patch

    override fun toString(): String {
        return "$major.$minor.$patch"
    }
}

fun Iterable<Version>.max(): Version? {
    return maxBy { it.major }?.major
        ?.let { maxMajor -> filter { it.major == maxMajor } }
        ?.let { maxMajorVersions ->
            maxMajorVersions.maxBy { it.minor }?.minor
                ?.let { maxMinor -> maxMajorVersions.filter { it.minor == maxMinor } }
        }
        ?.maxBy { it.patch }
}
