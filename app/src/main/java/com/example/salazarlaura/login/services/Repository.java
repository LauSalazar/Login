package com.example.salazarlaura.login.services;


import com.example.salazarlaura.login.models.User;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

public class Repository {

    private IServices iServices;

    public Repository() {
        ServiceFactory serviceFactory = new ServiceFactory();
        iServices = (IServices) serviceFactory.getInstanceService(IServices.class);

    }

    public User getUser(String user, String password) throws IOException {
        try{
            Call call = iServices.getUser(user, password);
            Response<User> response = call.execute();
            if(response.errorBody() != null){
                throw  defaultError();
            }
            return response.body();
        } catch (IOException e) {
            throw defaultError();
        }

    }

    private IOException defaultError(){
        return new IOException("Ha ocurrido un error");
    }


}
