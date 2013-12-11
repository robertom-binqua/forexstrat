package com.binqua.forexstrat.feedreader.core.client;

import com.binqua.forexstrat.feedreader.core.actions.FeedReaderResponseAction;
import org.junit.Test;

import java.util.concurrent.ExecutorService;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ExecutorServiceClientRunnerTest {

    private final ClientFactory clientFactory = mock(ClientFactory.class);
    private final ExecutorService executorService = mock(ExecutorService.class);
    private final ClientCommands clientCommands = mock(ClientCommands.class);
    private final FeedReaderResponseAction firstAction = mock(FeedReaderResponseAction.class);
    private final FeedReaderResponseAction secondAction = mock(FeedReaderResponseAction.class);

    private final ExecutorServiceClientRunner executorServiceClientRunner = new ExecutorServiceClientRunner(executorService, clientFactory);

    @Test
    public void aClientIsExecutedWithCommandsAndActions() {
        final Runnable client = mock(Runnable.class);
        final FeedReaderResponseAction[] actionToBeExecutedOnFeedReaderResponse = {firstAction, secondAction};

        when(clientFactory.createAClientWith(clientCommands, actionToBeExecutedOnFeedReaderResponse)).thenReturn(client);

        executorServiceClientRunner.run(clientCommands, actionToBeExecutedOnFeedReaderResponse);

        verify(executorService).execute(client);

    }

    @Test
    public void stopShoutdownTheExecutor() {
        executorServiceClientRunner.stop();

        verify(executorService).shutdown();
    }

}
