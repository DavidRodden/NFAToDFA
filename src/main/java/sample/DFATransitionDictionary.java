package sample;

import sample.state_machine.DFANode;
import sample.state_machine.NFANode;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by David on 12/4/2016.
 */
public class DFATransitionDictionary {
    private final List<DFATransitionEntry> transitionEntries;

    public DFATransitionDictionary(final List<NFANode> nfaNodes) {
        transitionEntries = new ArrayList<>();
        nfaNodes.forEach(n -> {
            n.getTargetArrows().forEach(a -> {
                a.getTransitionWords().stream().distinct().collect(Collectors.toList()).forEach(w -> {
                    final int value = a.getTarget().getValue();
                    if (!transitionEntries.stream().filter(e -> e.getTransitionWord().equals(w)).findAny().isPresent())
                        transitionEntries.add(new DFATransitionEntry(w, value));
                    else
                        transitionEntries.stream().filter(e -> e.getTransitionWord().equals(w)).forEach(entry -> entry.add(value));
                });
            });
        });
    }

    public List<DFATransitionEntry> getTransitionEntries() {
        return transitionEntries;
    }
}
