package temporal;

import io.temporal.workflow.SignalMethod;
import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface RecruitmentWorkFlow {

    @WorkflowMethod
    String initiateApplicationProcessing();

    @SignalMethod
    void emailSignal();

    @SignalMethod
    void assessmentSignal(Boolean decision);

    @SignalMethod
    void interviewSignal(int score);

}
