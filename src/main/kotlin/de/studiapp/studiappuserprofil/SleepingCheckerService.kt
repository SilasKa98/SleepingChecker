package de.studiapp.studiappuserprofil

import org.springframework.stereotype.Service


@Service
class SleepingCheckerService(val database: UrlRepository) {

    //get Data from the Database
    fun getAllData(): Iterable<SleepingCheckerModel> = database.findAll()

    //save Data to the Database
    fun postData(profileData: SleepingCheckerModel){
        database.save(profileData)
    }

    //delete Data from Database
    fun deleteDataById(id: String){
        val exists = database.existsById(id)
        if(!exists)
            throw Exception("Profile with id $id is not existing")

        database.deleteById(id)
    }
}



