/*
 * Software License Agreement (BSD License)
 *
 * Copyright 2014 Marc Pujol <mpujol@iiia.csic.es>.
 *
 * Redistribution and use of this software in source and binary forms, with or
 * without modification, are permitted provided that the following conditions
 * are met:
 *
 *   Redistributions of source code must retain the above
 *   copyright notice, this list of conditions and the
 *   following disclaimer.
 *
 *   Redistributions in binary form must reproduce the above
 *   copyright notice, this list of conditions and the
 *   following disclaimer in the documentation and/or other
 *   materials provided with the distribution.
 *
 *   Neither the name of IIIA-CSIC, Artificial Intelligence Research Institute
 *   nor the names of its contributors may be used to
 *   endorse or promote products derived from this
 *   software without specific prior written permission of
 *   IIIA-CSIC, Artificial Intelligence Research Institute
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package es.csic.iiia.ms.op;

/**
 * Available combination modes.
 */
public enum Combine {
    
    /**
     * Perform the combine operator using product.
     */
    PRODUCT {
        @Override
        public double getNeutralValue() {
            return 1;
        }

        @Override
        public double eval(double x, double y) {
            return x * y;
        }

        @Override
        public double negate(double x) {
            return Double.isInfinite(x) ? 0 : 1 / x;
        }

        @Override
        public double invert(double x) {
            throw new UnsupportedOperationException("Product has no inverse");
        }
    },

    /**
     * Perform the combine operator using addition.
     */
    SUM {
        @Override
        public double getNeutralValue() {
            return 0;
        }

        @Override
        public double eval(double x, double y) {
            return x + y;
        }

        @Override
        public double negate(double x) {
            return Double.isInfinite(x) ? x : -x;
        }

        @Override
        public double invert(double x) {
            return -x;
        }
    };

    /**
     * Performs the combination of the given values according to the current combination mode.
     *
     * @param x first value.
     * @param y second value.
     * @return combination result.
     */
    public abstract double eval(double x, double y);

    /**
     * Returns the neutral value of the combination mode.
     *
     * This is, it returns <em>0</em> when SUM-combining, or <em>1</em>
     * when PRODUCT-combining.
     *
     * @return combination <em>neutral</em> value.
     */
    public abstract double getNeutralValue();

    /**
     * Returns the inverse of the given value.
     *
     * Given {@link #eval(x, y)} and {@link #getNeutralValue()}, this function returns the value
     * such that
     * <code>
     * eval(x, negate(x)) == getNeutralValue()
     * </code> with the exception of nogoods, where:
     * <code>
     * negate(nogood) == nogood
     * </code>
     *
     * @param x value.
     * @return the inverse of the given value.
     */
    public abstract double negate(double x);

    /**
     * Returns the inverse of the given value.
     *
     * Given {@link #eval(x, y)} and {@link #getNeutralValue()}, this function returns the value
     * such that
     * <code>
     * eval(x, inverse(x)) == getNeutralValue()
     * </code> with the exception of nogoods, where:
     * <code>
     * negate(nogood) == -nogood
     * </code>
     *
     * This function should only be used to turn maximization problems into minimization ones,
     * or vice-versa.
     *
     * @param x value.
     * @return the inverse of the given value.
     */
    public abstract double invert(double x);

}
