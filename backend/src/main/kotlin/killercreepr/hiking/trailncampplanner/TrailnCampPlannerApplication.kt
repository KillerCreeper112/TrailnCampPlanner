package killercreepr.hiking.trailncampplanner

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class TrailnCampPlannerApplication

fun main(args: Array<String>) {
  runApplication<TrailnCampPlannerApplication>(*args)
}
