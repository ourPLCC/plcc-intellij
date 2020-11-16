package edu.rit.cs.plcc.jetbrainsPlugin.lang;

import com.intellij.openapi.util.TextRange;
import com.intellij.psi.*;
import edu.rit.cs.plcc.jetbrainsPlugin.lang.parser_model.impl.PLCCUncapturedTokenTypeImpl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static java.util.Objects.nonNull;

public class PlccReference extends PsiReferenceBase<PsiElement> implements PsiPolyVariantReference {

    private final String key;

    public PlccReference(@NotNull PsiElement element, TextRange rangeInElement) {
        super(element, rangeInElement);
        System.out.println("New PLCC Reference!");
        key = element.getText().substring(rangeInElement.getStartOffset(), rangeInElement.getEndOffset());
    }

    @Override
    public @NotNull ResolveResult @NotNull [] multiResolve(boolean incompleteCode) {
        System.out.println("MyElement = " + myElement);
        if (myElement instanceof PLCCUncapturedTokenTypeImpl) {
            var references = PlccUtil.findRegexReference(myElement, key);
            if (nonNull(references)) {
                return new ResolveResult[] { new PsiElementResolveResult(references) };
            } else {
                return new ResolveResult[0];
            }

        } else {
            return new ResolveResult[0];
        }
//        Project project = myElement.getProject();
//        final List<SimpleProperty> properties = PlccUtil.findProperties(project, key);
//        List<ResolveResult> results = new ArrayList<>();
//        for (SimpleProperty property : properties) {
//            results.add(new PsiElementResolveResult(property));
//        }
//        return results.toArray(new ResolveResult[results.size()]);
    }

    @Override
    public @Nullable PsiElement resolve() {
        ResolveResult[] resolveResults = multiResolve(false);
        return resolveResults.length == 1 ? resolveResults[0].getElement() : null;
    }
}
