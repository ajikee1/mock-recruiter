package temporal;

import io.temporal.activity.ActivityOptions;
import io.temporal.workflow.*;

import java.time.Duration;

public class RecruitmentWorkFlowImp implements RecruitmentWorkFlow {

    private final ActivityOptions options = ActivityOptions.newBuilder().setStartToCloseTimeout(Duration.ofSeconds(30)).build();

    private final RecruitmentActivity recruitmentActivity = Workflow.newActivityStub(RecruitmentActivity.class, options);

    public Boolean sendContactEmail = false;
    public Boolean isAssessmentComplete = false;
    public Boolean isInterviewCompleted = false;

    private Boolean assessmentDecision;
    private Boolean interviewDecision;

    @Override
    public String initiateApplicationProcessing() {

        Promise<String> addRecruitPromise = Async.function(recruitmentActivity::acceptApplication);
        String email = addRecruitPromise.get();
        System.out.println("Recruit emailAddress: " + email);


        System.out.println("Waiting on recruiter to initiate contact email");
        Workflow.await(() -> sendContactEmail);


        Promise<Boolean> emailPromise = Async.function(recruitmentActivity::sendConfirmationEmail);
        Boolean emailSendStatus = emailPromise.get();
        System.out.println("emailSendStatus: " + emailSendStatus);


        System.out.println("*** Waiting on vendor to send assessment decision ***");
        Workflow.await(() -> isAssessmentComplete);


        if (assessmentDecision) {
            System.out.println("*** Waiting on manager to send interview decision ***");
            Workflow.await(() -> isInterviewCompleted);

            if (interviewDecision) {
                recruitmentActivity.hire();
            } else {
                recruitmentActivity.reject();
            }
        }

        return "recruitment workflow complete";
    }

    @Override
    public void emailSignal() {
        Workflow.sleep(Duration.ofSeconds(3));

        this.sendContactEmail = true;

    }

    @Override
    public void assessmentSignal(Boolean decision) {
        Workflow.sleep(Duration.ofSeconds(3));

        assessmentDecision = recruitmentActivity.performAssessment(decision);
        this.isAssessmentComplete = true;
    }


    @Override
    public void interviewSignal(int score) {
        Workflow.sleep(Duration.ofSeconds(3));

        recruitmentActivity.interview(score);
        interviewDecision = false;
        this.isInterviewCompleted = true;
    }
}
