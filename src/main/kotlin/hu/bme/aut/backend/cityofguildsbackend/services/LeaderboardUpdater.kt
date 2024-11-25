package hu.bme.aut.backend.cityofguildsbackend.services

import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class LeaderboardUpdater(
    private val leaderboardService: ILeaderboardService
) {

    private val logger = LoggerFactory.getLogger(LeaderboardUpdater::class.java)

    @Scheduled(fixedDelay = 3600000)  // One hour in milliseconds is 3600000
    fun scheduledUpdate() {
        leaderboardService.updateLeaderboard()
        logger.info("Refreshed leaderboard")
    }
}