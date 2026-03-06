// Copyright 2021-2025 NCSDecomp
// Licensed under the Business Source License 1.1 (BSL 1.1).
// See LICENSE.txt file in the project root for full license information.

package com.kotor.resource.formats.ncs.scriptnode;

import com.kotor.resource.formats.ncs.stack.StackEntry;

public class AActionArgExp extends ScriptRootNode implements AExpression {
   public AActionArgExp(int start, int end) {
      super(start, end);
      this.start = start;
      this.end = end;
   }

   /**
    * Action-typed arguments (e.g. AssignCommand(o, <action>)) are represented in the bytecode
    * as a mini-block of commands. When used as an expression, we must render just the action
    * expression (without statement semicolons/newlines), otherwise the decompiled NSS will not
    * compile to matching bytecode.
    */
   @Override
   public String toString() {
      if (this.children == null || this.children.isEmpty()) {
         return "/*action*/";
      }

      // Prefer the last expression-ish node in the block.
      for (int i = this.children.size() - 1; i >= 0; i--) {
         ScriptNode child = this.children.get(i);
         if (child instanceof AExpressionStatement) {
            AExpression exp = ((AExpressionStatement) child).exp();
            return exp != null ? exp.toString() : "/*action*/";
         }
         if (child instanceof AExpression) {
            return child.toString();
         }
      }

      return "/*action*/";
   }

   @Override
   public StackEntry stackentry() {
      return null;
   }

   @Override
   public void stackentry(StackEntry stackentry) {
   }
}

