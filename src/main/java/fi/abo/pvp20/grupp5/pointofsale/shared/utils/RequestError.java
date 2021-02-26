package fi.abo.pvp20.grupp5.pointofsale.shared.utils;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class RequestError extends RuntimeException {
}
