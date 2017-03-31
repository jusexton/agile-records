package database
/**
 * Entity class which holds information about a population session.
 */
class PopulateStatistics {
    double time
    int studentsAdded
    HashMap<String, Integer> takenUsernames

    PopulateStatistics() {
        takenUsernames = new HashMap<>()
    }

    void nameAttempt(String name) {
        if (takenUsernames.keySet().contains(name)) {
            takenUsernames.put(name, takenUsernames.get(name) + 1)
        } else {
            takenUsernames.put(name, 1)
        }
    }
}
