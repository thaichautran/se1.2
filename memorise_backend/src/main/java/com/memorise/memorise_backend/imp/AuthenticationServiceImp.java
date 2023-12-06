package com.memorise.memorise_backend.imp;

import com.memorise.memorise_backend.payload.request.SignUpRequest;

public interface AuthenticationServiceImp {
    boolean isSignUp(SignUpRequest signUpRequest);
}
