package temporal;

import io.temporal.api.common.v1.WorkflowExecution;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.workflow.Workflow;

import java.time.Duration;

public class RecruitmentWorkflowRunner {

    public static void main(String[] args) {

        WorkflowServiceStubs service = WorkflowServiceStubs.newInstance();
        WorkflowClient client = WorkflowClient.newInstance(service);
        WorkflowOptions options = WorkflowOptions.newBuilder().setTaskQueue(RecruitmentWorker.API_TASK_QUEUE).build();
        RecruitmentWorkFlow workflow = client.newWorkflowStub(RecruitmentWorkFlow.class, options);

        /* Asynchronous execution. This process will exit after making this call */
        WorkflowExecution we = WorkflowClient.start(workflow::initiateApplicationProcessing);

        workflow.emailSignal();

        workflow.assessmentSignal(true);

        workflow.interviewSignal(70);

        System.exit(0);
    }
}
