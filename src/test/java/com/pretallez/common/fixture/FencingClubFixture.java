package com.pretallez.common.fixture;

import com.pretallez.domain.fencingclub.entity.FencingClub;
import org.springframework.test.util.ReflectionTestUtils;

public class FencingClubFixture {

    public static FencingClub fencingClub() {
        String type = "SABRE";
        String contact = "0212345678";
        String address = "seoul";
        String description = "fencing club";
        String gearExist = "Y";

        return FencingClub.of(type, contact, address, description, gearExist);
    }

    public static FencingClub fakeFencingClub(Long id) {
        String type = "SABRE";
        String contact = "0212345678";
        String address = "seoul";
        String description = "fencing club";
        String gearExist = "Y";
        FencingClub fencingClub = FencingClub.of(type, contact, address, description, gearExist);
        ReflectionTestUtils.setField(fencingClub, "id", id);

        return fencingClub;
    }
}
