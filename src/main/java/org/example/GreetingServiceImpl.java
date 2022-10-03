package org.example;
import com.example.grpc.GreetingServiceOuterClass;
import com.example.grpc.GreetingServiceGrpc;
import io.grpc.stub.StreamObserver;
//import org.example.Entity.Case;
//import org.example.Repository.CaseRepository;
import java.util.ArrayList;
import java.util.List;


public class GreetingServiceImpl extends GreetingServiceGrpc.GreetingServiceImplBase {

//    @Autowired
//    CaseRepository caseRepository;

    static List<GreetingServiceOuterClass.TaskResponse> productsEntities = new ArrayList<>();
    public GreetingServiceImpl() {
        productsEntities.add(GreetingServiceOuterClass.TaskResponse.newBuilder().setId(1).setName("renat").setDescription("da").setDate("01.02.03").build());
        productsEntities.add(GreetingServiceOuterClass.TaskResponse.newBuilder().setId(2).setName("re321321").setDescription("ddsfds").setDate("01.02.03").build());
    }

    @Override
    public void changeTask(GreetingServiceOuterClass.TaskRequest request, StreamObserver<GreetingServiceOuterClass.TaskResponse> responseObserver) {
        GreetingServiceOuterClass.TaskResponse response =
                GreetingServiceOuterClass.TaskResponse.newBuilder()
                        .setDate(request.getDate())
                        .setName(request.getName())
                        .setId(request.getId())
                        .setDescription(request.getDescription())
                        .build();
        for (int i = 0; i < productsEntities.size(); i++)
            if (productsEntities.get(i).getId() == request.getId()){
                productsEntities.remove(productsEntities.get(i));
                productsEntities.add(i, response);
            }

        System.out.println("\nTask changed:");
        System.out.println(response);

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void addTask(GreetingServiceOuterClass.TaskRequest request, StreamObserver<GreetingServiceOuterClass.TaskResponse> responseObserver) {
        GreetingServiceOuterClass.TaskResponse response =
                GreetingServiceOuterClass.TaskResponse.newBuilder()
                        .setDate(request.getDate())
                        .setName(request.getName())
                        .setId(productsEntities.get(productsEntities.size() - 1).getId() + 1)
                        .setDescription(request.getDescription())
                        .build();
        productsEntities.add(response);
        System.out.println("\nTask added:");
        System.out.println(response);

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void deleteTask(GreetingServiceOuterClass.TaskRequest request, StreamObserver<GreetingServiceOuterClass.Done> responseObserver) {
        for (int i = 0; i < productsEntities.size(); i++) {
            if (productsEntities.get(i).getId() == request.getId())
                productsEntities.remove(productsEntities.get(i));
        }

        GreetingServiceOuterClass.Done response = GreetingServiceOuterClass.Done
                .newBuilder().setBoolDone(true).build();


        System.out.println("\nTask deleted:");
        System.out.println(response);

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void allTasks(GreetingServiceOuterClass.Done request, StreamObserver<GreetingServiceOuterClass.AllTasksResponse> responseObserver) {
        GreetingServiceOuterClass.AllTasksResponse response = GreetingServiceOuterClass.AllTasksResponse.
                newBuilder().addAllCases(productsEntities).build();

        System.out.println("\nAll tasks:");
        System.out.println(response);

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

}
