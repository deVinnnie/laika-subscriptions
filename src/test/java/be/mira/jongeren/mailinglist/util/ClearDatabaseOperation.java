package be.mira.jongeren.mailinglist.util;

import com.ninja_squad.dbsetup.operation.Operation;

import static com.ninja_squad.dbsetup.Operations.deleteAllFrom;

public class ClearDatabaseOperation {
    public static Operation operation = deleteAllFrom("subscription_list_subscribers", "subscriber", "subscription_list");
}
