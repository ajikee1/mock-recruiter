package api;

import io.temporal.api.common.v1.WorkflowExecution;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import io.temporal.serviceclient.WorkflowServiceStubs;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import temporal.RecruitmentWorkFlow;
import temporal.RecruitmentWorker;

@RestController
public class SendApiSignals {

    WorkflowServiceStubs service = WorkflowServiceStubs.newInstance();
    WorkflowClient client = WorkflowClient.newInstance(service);
    WorkflowOptions options = WorkflowOptions.newBuilder().setTaskQueue(RecruitmentWorker.API_TASK_QUEUE).build();
    RecruitmentWorkFlow workflow = client.newWorkflowStub(RecruitmentWorkFlow.class, options);

    @RequestMapping(value="/initiate/")
    public void initiate(){
        WorkflowExecution we = WorkflowClient.start(workflow::initiateApplicationProcessing);
    }

    @RequestMapping(value="/emailSignal/")
    public void sendEmailSignal(){
        workflow.emailSignal();
    }

    @RequestMapping(value="/assessmentSignal/")
    public void sendAssessmentSignal(){
        workflow.assessmentSignal(true);
    }

    @RequestMapping(value="/interviewSignal/")
    public void sendInterviewSignal(){
        workflow.interviewSignal(70);
    }
}
