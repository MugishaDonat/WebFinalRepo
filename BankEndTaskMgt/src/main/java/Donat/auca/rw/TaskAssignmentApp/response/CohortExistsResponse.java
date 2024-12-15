package Christian.auca.rw.AssignmentSubmissionApp.response;

public class CohortExistsResponse {
    private boolean exists;

    public CohortExistsResponse(boolean exists) {
        this.exists = exists;
    }

    public boolean isExists() {
        return exists;
    }

    public void setExists(boolean exists) {
        this.exists = exists;
    }
}
