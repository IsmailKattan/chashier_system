package com._32bit.project.cashier_system.DTO;


import lombok.*;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

public record AuthRequestDto(String name, String username, String password, List<String> roles) {

}
