package edu.rit.cs.plcc.jetbrainsPlugin.lang;

import com.intellij.psi.PsiElement;
import edu.rit.cs.plcc.jetbrainsPlugin.lang.parser_model.PLCCTokenDef;
import edu.rit.cs.plcc.jetbrainsPlugin.lang.parser_model.PLCCTypes;
import edu.rit.cs.plcc.jetbrainsPlugin.lang.parser_model.impl.PLCCRegexDefImpl;
import edu.rit.cs.plcc.jetbrainsPlugin.lang.parser_model.impl.PLCCRegexDefsImpl;
import edu.rit.cs.plcc.jetbrainsPlugin.lang.parser_model.impl.PLCCTokenDefImpl;

import java.util.Arrays;
import java.util.Optional;

public class PlccUtil {

    public static PLCCTokenDef findRegexReference(PsiElement topElement, String text) {
        System.out.println("First Child: " + topElement.getFirstChild());
        Optional<PsiElement> regexDefsOpt = Arrays.stream(topElement.getChildren())
                .filter(x -> x instanceof PLCCRegexDefsImpl)
                .findFirst();
        if (regexDefsOpt.isEmpty()) {
            System.out.println("NULE");
            return null;
        }

        var regexDefs = regexDefsOpt.get();
        System.out.println("regex defs: " + regexDefs);

        var regexDefStream = Arrays.stream(regexDefs.getChildren())
                .filter(x -> x instanceof PLCCRegexDefImpl);

        var s = regexDefStream.map(x -> {
            return Arrays.stream(x.getChildren())
                    .filter(y -> y instanceof PLCCTokenDefImpl).findFirst().get();
        }).filter(x -> {
            var l = ((PLCCTokenDefImpl) x).getNode().findChildByType(PLCCTypes.TOKEN_TYPE_NAME);
            assert l != null;
            return l.getText().equals(text);
        }).findFirst();

        if (s.isEmpty()) {
            System.out.println("NUUUl");
            return null;
        } else {
            return (PLCCTokenDef) s.get();
        }




    }
}
