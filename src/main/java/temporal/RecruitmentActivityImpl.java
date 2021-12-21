package temporal;

import io.temporal.activity.ActivityInterface;

@ActivityInterface
interface RecruitmentActivity {
    String acceptApplication();

    Boolean sendConfirmationEmail();

    Boolean performAssessment(Boolean complete);

    Boolean interview(int score);

    void hire();

    void reject();

}


public class RecruitmentActivityImpl implements RecruitmentActivity {

    @Override
    public String acceptApplication() {
        System.out.println("ACCEPTANCE STATUS: true");
        String email = "ajith@yopmail.com";
        return email;
    }

    @Override
    public Boolean sendConfirmationEmail() {
        System.out.println(" *** Application Received Email Sent *** ");
        return true;
    }

    @Override
    public Boolean performAssessment(Boolean complete) {
        System.out.println("ASSESSMENT STATUS:  " + complete);
        return complete;
    }

    @Override
    public Boolean interview(int interviewScore) {
        Boolean d = false;
        if (interviewScore >= 90) {
            d = true;
        }

        System.out.println("INTERVIEW STATUS:  " + d);
        return d;
    }

    @Override
    public void hire() {
        System.out.println(" *** Hired *** ");
    }

    @Override
    public void reject() {
        System.out.println(" *** Rejected *** ");
    }
}
