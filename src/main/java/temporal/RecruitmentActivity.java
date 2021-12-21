package temporal;

import io.temporal.activity.ActivityInterface;

@ActivityInterface
public interface RecruitmentActivity {
    String acceptApplication();

    Boolean sendConfirmationEmail();

    Boolean performAssessment(Boolean complete);

    Boolean interview(int score);

    void hire();

    void reject();
}
