package de.studiapp.studiappuserprofil

import org.springframework.web.bind.annotation.*

//Rest Endpoint

@RestController
@RequestMapping("/sleepingChecker")
class SleepingCheckerResource(val profileService: SleepingCheckerService) {

    @GetMapping
    fun getAllUrls(): Iterable<SleepingCheckerModel> {
        return profileService.getAllData()
    }

    @PostMapping
    fun postUrl(@RequestBody profileData: SleepingCheckerModel) {
        println(profileData)
        profileService.postData(profileData)
    }

    @DeleteMapping("/{id}")
    fun delUrl(@PathVariable id: String){
        profileService.deleteDataById(id)
    }
}


