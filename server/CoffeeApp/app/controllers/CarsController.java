package controllers;

import models.domain.Car;
import play.data.Form;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.proof.*;

import javax.inject.Inject;
import java.util.Set;


public class CarsController extends Controller {

    @Inject
    FormFactory formFactory;


    public Result index() {
        //Set<Car> cars = Car.allCars();
        return ok(index.render("cars"));
    }


    public Result show(Integer id) {
        return TODO;
    }

    public Result create() {
        /*try {
            //JsonNode request = request().body().asJson();

            //if (!request.has("model"))
            //    return badRequest("model is required");
            //Car car = Json.fromJson(request, Car.class);

            Form<Car> form = formFactory.form(Car.class).bindFromRequest();
            if(form.hasErrors())
                return badRequest(form.errorsAsJson());

            Car car = form.get();
            car.insert();

            return ok(Json.toJson(car));
        }catch (Exception e){
            return internalServerError("Oops!");

        }*/
        return internalServerError("Oops!");
    }

    public Result edit(Integer id) {
        return TODO;
    }

    public Result save() {
        return TODO;
    }

    public Result destroy(Integer id) {

        return TODO;
    }

    public Result add() {
        return TODO;
    }

    public Result update() {
        return TODO;
    }
}
