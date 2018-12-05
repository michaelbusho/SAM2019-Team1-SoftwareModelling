package com.sam2019.ui;

import com.sam2019.model.Paper;
import com.sam2019.model.SQLiteConnection;
import com.sam2019.model.User;
import spark.*;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.halt;

public class PcmReviewPaperController implements TemplateViewRoute {

    @Override
    public ModelAndView handle(Request request, Response response) {
        // start building the View-Model
        final Map<String, Object> vm = new HashMap<>();

        //Create Session
        final Session session = request.session();

        User currentUser = session.attribute("user");
        String paperID = request.params(":papername");

        Paper currentPaper = SQLiteConnection.getPaper(paperID);

        if (request.requestMethod() == WebServer.GET_METHOD) {


            vm.put("paper", currentPaper);
            vm.put("user", currentUser);
            vm.put("title", "Register Page");

            return new ModelAndView(vm, "pcmReviewPaper.ftl");
        }
        else if(request.requestMethod() == WebServer.POST_METHOD){
            String reviewComment = request.queryParams("comment");
            String rating = request.queryParams("input-1");

           if( SQLiteConnection.insertReview(paperID,currentUser.getUserName(),reviewComment, rating)){

                //deleteFrom Reviewer

                SQLiteConnection.pcmCompleteReview(currentUser.getUserName(), currentPaper.getId());
               response.redirect(WebServer.PROFILE_URL);
               halt();
               return null;
           }else{

               vm.put("paper", currentPaper);
               vm.put("user", currentUser);
               vm.put("title", "Register Page");
               return new ModelAndView(vm, "pcmReviewPaper.ftl");
           }


        }else{
            return null;
        }

    }
}
