package kz.yerakh.animaltrackerservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class EntryAlreadyExistException extends RuntimeException {
}
