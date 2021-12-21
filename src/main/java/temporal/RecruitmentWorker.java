package temporal;

import io.temporal.client.WorkflowClient;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;

public class RecruitmentWorker {

    public static String API_TASK_QUEUE = "COMMON_QUEUE";

    public static void main(String[] args) {

        WorkflowServiceStubs service = WorkflowServiceStubs.newInstance();
        WorkflowClient client = WorkflowClient.newInstance(service);
        WorkerFactory factory = WorkerFactory.newInstance(client);
        Worker worker = factory.newWorker(API_TASK_QUEUE);

        worker.registerWorkflowImplementationTypes(RecruitmentWorkFlowImp.class);
        worker.registerActivitiesImplementations(new RecruitmentActivityImpl());

        factory.start();
    }
}
