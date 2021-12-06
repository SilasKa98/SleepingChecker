package de.studiapp.studiappuserprofil

import org.springframework.data.repository.CrudRepository


interface UrlRepository : CrudRepository<SleepingCheckerModel, String> {

}

