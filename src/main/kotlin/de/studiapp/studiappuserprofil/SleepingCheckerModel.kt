package de.studiapp.studiappuserprofil

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("URLSTOREQUEST")
data class SleepingCheckerModel(@Id val id: String?, val url: String)
