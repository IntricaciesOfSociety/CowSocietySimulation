package cowMovement;

abstract class Movement {

    Start test;

    abstract void defineMovementAction();

    public interface Start {
        void startMove();
    }

    interface Start2 {
        void startMove2();
    }

    interface Start3 {
        void startMove3();
    }
}
