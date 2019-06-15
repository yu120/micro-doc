package org.micro.doc;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MicroKind {

    // ====

    API_NOTE("apiNote"),
    IMPL_NOTE("implNote"),
    IMPL_SPEC("implSpec"),
    ;

    public final String tagName;

}
