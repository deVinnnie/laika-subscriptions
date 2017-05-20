package be.mira.jongeren.mailinglist.util;

import com.ninja_squad.dbsetup.operation.Operation;

import static com.ninja_squad.dbsetup.Operations.deleteAllFrom;
import static com.ninja_squad.dbsetup.Operations.insertInto;
import static com.ninja_squad.dbsetup.Operations.sequenceOf;

/**
 * DBSetup Operation which adds the default SubscriptionLists to the db.
 */
public class CreateListsOperation {

    public static Operation operation = sequenceOf(
            deleteAllFrom("subscription_list"),
            insertInto("subscription_list")
                    .columns("id","title")
                    .values("10", "main-sequence")
                    .values("20", "supernova")
                    .build()
    );
}


