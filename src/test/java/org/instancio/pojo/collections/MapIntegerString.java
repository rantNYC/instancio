package org.instancio.pojo.collections;

import lombok.Getter;
import lombok.ToString;

import java.util.Map;

@Getter
@ToString
public class MapIntegerString {

    private Map<Integer, String> mapField;
}