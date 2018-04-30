//package controllers.parsers.bodyParser;
//
//import akka.util.ByteString;
//import com.fasterxml.jackson.databind.JsonNode;
//import models.UserClient;
//import play.libs.F;
//import play.libs.streams.Accumulator;
//import play.mvc.BodyParser;
//import play.mvc.Http;
//import play.mvc.Result;
//import play.mvc.Results;
//
//import javax.inject.Inject;
//import java.util.concurrent.Executor;
//
//
///**
// * Created by nisa on 09/08/17.
// */
//// reference: https://www.playframework.com/documentation/2.5.x/JavaBodyParsers
//public class UserClientBodyParser implements BodyParser<UserClient> {
//
//    private BodyParser.Json jsonParser;
//    private Executor executor;
//
//    @Inject
//    public UserClientBodyParser(BodyParser.Json jsonParser, Executor executor) {
//        this.jsonParser = jsonParser;
//        this.executor = executor;
//    }
//
//    @Override
//    public Accumulator<ByteString, F.Either<Result, UserClient>> apply(Http.RequestHeader request) {
//        Accumulator<ByteString, F.Either<Result, JsonNode>> jsonAccumulator = jsonParser.apply(request);
//        return jsonAccumulator.map(resultOrJson -> {
//            if (resultOrJson.left.isPresent()) {
//                return F.Either.Left(resultOrJson.left.get());
//            } else {
//                JsonNode json = resultOrJson.right.get();
//                try {
//                    UserClient userClient = play.libs.Json.fromJson(json, UserClient.class);
//                    return F.Either.Right(userClient);
//                } catch (Exception e) {
//                    return F.Either.Left(Results.badRequest(
//                            "Unable to read UserClient from json: " + e.getMessage()));
//                }
//            }
//        }, executor);
//    }
//}
