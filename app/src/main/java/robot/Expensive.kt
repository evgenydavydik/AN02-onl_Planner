package robot

class Expensive {
    fun expensiveRobot(robots: List<Robot>): Robot? {
        var priceMax = 0
        for (robot in robots) {
            if (priceMax < robot.priceRobot) {
                priceMax = robot.priceRobot
            }
        }
        for (robot in robots) {
            if (priceMax == robot.priceRobot) {
                return robot
            }
        }
        return robots[0]
    }
}