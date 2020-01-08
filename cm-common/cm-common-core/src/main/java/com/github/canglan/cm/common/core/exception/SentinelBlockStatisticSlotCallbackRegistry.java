package com.github.canglan.cm.common.core.exception;

import com.alibaba.csp.sentinel.context.Context;
import com.alibaba.csp.sentinel.slotchain.ProcessorSlotEntryCallback;
import com.alibaba.csp.sentinel.slotchain.ProcessorSlotExitCallback;
import com.alibaba.csp.sentinel.slotchain.ResourceWrapper;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author bairitan
 * @date 2020/1/7
 */
@Component
@Slf4j
public class SentinelBlockStatisticSlotCallbackRegistry implements ProcessorSlotEntryCallback, ProcessorSlotExitCallback {

  @Override
  public void onPass(Context context, ResourceWrapper resourceWrapper, Object o, int i, Object... objects) throws Exception {
    log.debug(" on pass ===================================");
  }

  @Override
  public void onBlocked(BlockException e, Context context, ResourceWrapper resourceWrapper, Object o, int i, Object... objects) {
    log.debug(" on blocked ===================================");
  }

  @Override
  public void onExit(Context context, ResourceWrapper resourceWrapper, int i, Object... objects) {
    log.debug(" on exit ===================================");
  }
}
